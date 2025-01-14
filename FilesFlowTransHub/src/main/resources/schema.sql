CREATE TABLE fft_service_main (
    service_id VARCHAR(255) PRIMARY KEY,
    service_description VARCHAR(255),
    service_name VARCHAR(255),
    service_status INT,
    source_location VARCHAR(255),
    source_port VARCHAR(255),
    source_path VARCHAR(255),
    source_id VARCHAR(255),
    source_cred VARCHAR(255),
    target_location VARCHAR(255),
    target_port VARCHAR(255),
    target_path VARCHAR(255),
    target_id VARCHAR(255),
    target_cred VARCHAR(255)
);

CREATE TABLE fft_case_main (
	case_create_datetime timestamp NULL,
	case_id varchar(29) PRIMARY KEY,
	case_status varchar(2) NULL,
	case_update_datetime timestamp NULL,
	service_id varchar(10) NULL
);

CREATE TABLE fft_file_main (
	case_create_datetime timestamp NULL,
	folder_id varchar(29) PRIMARY KEY,
	folder_path varchar(200) NULL
);

CREATE TABLE fft_case_details (
	id varchar(40)  PRIMARY KEY,
	case_id varchar(29) NOT NULL,
	case_status varchar(2) NULL,
	update_datetime timestamp NULL
);

CREATE TABLE fft_file_details (
	id numeric(19) PRIMARY KEY,
	file_name varchar(1000) NOT NULL,
	file_size numeric(20) NULL,
	folder_id varchar(29) NOT NULL,
	last_upload_datetime timestamp NULL,
	hash_value varchar(256) NULL
);

CREATE TABLE fft_exception_main (
	id numeric(19) PRIMARY KEY,
	case_id varchar(29) NULL,
	exception_datetime timestamp NULL,
	exception_message varchar(3000) NULL,
	service_id varchar(10) NULL
);
CREATE TABLE fft_allowed_host (
	id numeric(19) PRIMARY KEY,
	allowed_host VARCHAR(255) 
);