package com.FilesFlowTransHub.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.FilesFlowTransHub.dto.ConnectionInfo;
import com.FilesFlowTransHub.dto.FileInfo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPFileTransUtils {
 
	/**
	 * 建立sftp連線
	 * @param info
	 * @return
	 * @throws Exception
	 */
    private static ChannelSftp newSFtpConnect(ConnectionInfo info) throws Exception {
        JSch jsch = new JSch();
        try {
        	Properties sshConfig = new Properties();
            // ssh的client端配置有一個選項StrictHostKeyChecking,預設是ask(yes>ask>no),
            // 改no才會將主機key再連接時自動加入known_hosts
            sshConfig.put("StrictHostKeyChecking", "no");
            sshConfig.put("userauth.gssapi-with-mic", "no");
            //sshConfig.put("cipher.s2c", "aes128-cbc");
            //sshConfig.put("cipher.c2s", "aes128-cbc");
            Session session = jsch.getSession(info.getAccount(), info.getHost(), info.getPort());
            session.setPassword(info.getCred());
            session.setConfig(sshConfig);
            session.setTimeout(1000);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            changeDir(sftp, info.getRemoteDir());
            return sftp;
        } catch (Exception e) {
        	System.out.println( e.getMessage());
        	throw e;
        }
 
    }
    /**
     * 切換目錄
     * @param client
     * @param path
     */
    private static void changeDir(ChannelSftp client, String path) {
        try {
            // 路徑開頭為/ ,切換至根目錄
            if (path.startsWith("/")) {
                client.cd("/");
            }

            String[] dirList = path.split("[./\\\\]");
            if (dirList.length != 0) {
                for (String dir : dirList) {
                    if (dir.length() <= 0) {
                        continue;
                    }
                    doChangeDir(client, dir);
                }
            }
        } catch (SftpException e) {
        	System.out.println( e.getMessage());
        }
    }
    /**
     * 切換目錄
     * @param sftp
     * @param dir
     * @throws SftpException
     */
    private static void doChangeDir(ChannelSftp sftp, String dir) throws SftpException {
        try {
            sftp.cd(dir);
        } catch (SftpException e) {
            throw e;
        }
    }
    /**
     * 測試連線
     * @param info
     * @return
     * @throws Exception
     */
    public static boolean isConnectionSuccess(ConnectionInfo info) throws Exception {
        boolean ftpStatus;
        ChannelSftp sftp = null;
        try {
            sftp = newSFtpConnect(info);
            ftpStatus = sftp.isConnected();
        } finally {
            closeQuietly(sftp);
        }
        return ftpStatus;
    }
    /**
     * 關閉連線
     * @param sftp
     */
    private static void closeQuietly(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                sftp.quit();
                sftp.getSession().disconnect();
                sftp.disconnect();
            }
        } catch (JSchException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * 檔案清單
     * @param info
     * @return
     * @throws Exception
     */
    public static List<FileInfo> listFiles(ConnectionInfo info) throws Exception {
    	ChannelSftp sftp = null;
    	List<FileInfo> ret = new ArrayList<>();
        try {
        	sftp = newSFtpConnect(info);
        	sftp.cd(info.getRemoteDir());
            
            // 列出當前路徑下的文件
            Vector<?> fileList = sftp.ls(".");
            
            for (Object obj : fileList) {
                if (obj instanceof LsEntry) {
                    LsEntry entry = (LsEntry) obj;

                    if (!entry.getAttrs().isDir() && !entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                        ret.add(new FileInfo(entry.getFilename(), entry.getAttrs().getSize(), entry.getAttrs().getMtimeString()));
                        System.out.println("filename:"+entry.getFilename());
                    }
                }
            }
       
        } catch (Exception e) {  
            throw e;
        } finally {
            closeQuietly(sftp);
        }
        return ret;
  
    }
    /**
     * 下載檔案
     * @param info
     * @param fileName
     * @param destDir
     */
    public static void downFile(ConnectionInfo info, String fileName, String destDir) {
        ChannelSftp sftp = null;
        try {
            sftp = newSFtpConnect(info);
            
            // 下載
            String localFilePath = destDir + "/" + fileName;
            try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
                sftp.get(fileName, outputStream);
                System.out.println("File downloaded successfully to: " + localFilePath);
            } catch (IOException e) {
                //System.out.println("Error writing to file: " + e.getMessage());
            }
            
        } catch (Exception e) {
            // System.out.println("Error downloading file: " + e.getMessage());
        } finally {
            closeQuietly(sftp);
        }
    }
    /**
     * 上傳檔案
     * @param info
     * @param fileName
     * @param srcDir
     * @throws Exception
     */
    public static void uploadFiles(ConnectionInfo info, String fileName, String srcDir) throws Exception {
        ChannelSftp sftp = null;
        try {
            
            sftp = newSFtpConnect(info);

            
            File file = new File(srcDir, fileName);

            
            if (!file.exists() || !file.isFile()) {
                throw new IllegalArgumentException("File does not exist: " + file.getAbsolutePath());
            }

             
            uploadFile(sftp, file, info.getRemoteDir());

        } catch (Exception e) {
            // System.out.println("Error uploading file: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
             
            closeQuietly(sftp);
        }
    }

    /**
     * 上傳檔案
     * @param sftp
     * @param file
     * @param remoteDir
     * @throws Exception
     */
    private static void uploadFile(ChannelSftp sftp, File file, String remoteDir) throws Exception {
        try (FileInputStream fis = new FileInputStream(file)) {
            sftp.cd(remoteDir);
            sftp.put(fis, file.getName(), ChannelSftp.OVERWRITE);
            System.out.println("Uploaded file: " + file.getName() + " to " + remoteDir);
        } catch (SftpException | IOException e) {
            System.out.println("Error uploading file " + file.getName() + ": " + e.getMessage());
            throw new Exception("Error uploading file " + file.getName() + ": " + e.getMessage());
        }
    }
}

