package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
@Component
public class DatabaseConfig implements ApplicationRunner {

    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
        	String adminLogSql = "create table if not exists admin_log ("
        			+ "num INT(11) NOT NULL AUTO_INCREMENT,"
        			+ "userId VARCHAR(255) NOT NULL,"
        			+ "modifiedTable VARCHAR(255) NULL DEFAULT NULL,"
        			+ "admin_log VARCHAR(255) NULL DEFAULT NULL,"
        			+ "updateDate DATETIME,"
        			+ "PRIMARY KEY (num));";
        	
            String userSql = "create table if not exists tb_user ("
            		+ "userNo INT(11) NOT NULL AUTO_INCREMENT,"
            		+ "userId VARCHAR(255) NOT NULL,"
            		+ "userPw VARCHAR(256) NULL DEFAULT NULL,"
            		+ "userName VARCHAR(255) NOT NULL,"
            		+ "userAuth VARCHAR(255) NOT NULL,"
            		+ "appendDate DATETIME NULL DEFAULT NULL,"
            		+ "updateDate DATETIME NULL DEFAULT NULL,"
            		+ "PRIMARY KEY (userNo));";
            
            String ec2PriceSql = "create table if not exists ec2_pricing_comparison ("
            		+ "instanceType VARCHAR(100) NOT NULL,"
            		+ "price DOUBLE NULL DEFAULT NULL,"
            		+ "ec2_vCPU VARCHAR(100) NULL DEFAULT NULL,"
            		+ "ec2_memory VARCHAR(100) NULL DEFAULT NULL,"
            		+ "operatingSys VARCHAR(100) NOT NULL,"
            		+ "ec2_storage VARCHAR(100) NULL DEFAULT NULL,"
            		+ "networkPerformance VARCHAR(100) NULL DEFAULT NULL,"
            		+ "region VARCHAR(100) NOT NULL,"
            		+ "customPrice VARCHAR(100) NULL DEFAULT NULL,"
            		+ "PRIMARY KEY (instanceType, operatingSys, region));";
            
            String regionCode2NameSql = "create table if not exists region_code2name ("
            		+ "regionname VARCHAR(100) NOT NULL,"
            		+ "regioncode VARCHAR(100) NOT NULL,"
            		+ "PRIMARY KEY (regionname, regioncode));";
            
            String insertRegionCode2Name = "INSERT IGNORE INTO region_code2name VALUES (\"US East (N. Virginia)\",\"us-east-1\");"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"US East (Ohio)\",\"us-east-2\");\r\n" 
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"US West (N. California)\",\"us-west-1\");\r\n" 
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"US West (Oregon)\",\"us-west-2\");\r\n" 
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Africa (Cape Town)\",\"af-south-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Hong Kong)\",\"ap-east-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Jakarta)\",\"ap-southeast-3\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Mumbai)\",\"ap-south-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Osaka)\",\"ap-northeast-3\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Seoul)\",\"ap-northeast-2\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Tokyo)\",\"ap-northeast-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Sydney)\",\"ap-southeast-2\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Asia Pacific (Singapore)\",\"ap-southeast-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Canada (Central)\",\"ca-central-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"EU (Frankfurt)\",\"eu-central-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"EU (Ireland)\",\"eu-west-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"EU (London)\",\"eu-west-2\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"EU (Milan)\",\"eu-south-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"EU (Paris)\",\"eu-west-3\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"EU (Stockholm)\",\"eu-north-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Middle East (Bahrain)\",\"me-south-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"Middle East (UAE)\",\"me-central-1\");\r\n"
            		+ "INSERT IGNORE INTO region_code2name VALUES (\"South America (Sao Paulo)\",\"sa-east-1\");";
            
            String awsRowSql = "create table if not exists awsresource ("
            		+ "instanceID VARCHAR(100) NOT NULL,"
            		+ "times VARCHAR(100) NOT NULL,"
            		+ "resource VARCHAR(100) NOT NULL,"
            		+ "statistic VARCHAR(100) NOT NULL,"
            		+ "val BIGINT(20) NULL DEFAULT NULL,"
            		+ "sources VARCHAR(100) NULL DEFAULT NULL,"
            		+ "PRIMARY KEY (instanceID, times, resource, statistic));";
            
            String awsDiskRowSql = "create table if not exists awsresource_disk ("
            		+ "instanceID VARCHAR(100) NOT NULL,"
            		+ "diskID VARCHAR(100) NOT NULL,"
            		+ "times VARCHAR(100) NOT NULL,"
            		+ "resource VARCHAR(100) NOT NULL,"
            		+ "statistic VARCHAR(100) NOT NULL,"
            		+ "val BIGINT(20) NULL DEFAULT NULL,"
            		+ "sources VARCHAR(100) NULL DEFAULT NULL,"
            		+ "PRIMARY KEY (instanceID, diskID, times, resource, statistic));";
            
            String awsKeySql = "create table if not exists awsconfig_key ("
            		+ "num INT(11) NOT NULL AUTO_INCREMENT,"
            		+ "accountID VARCHAR(255) NULL DEFAULT NULL,"
            		+ "accessKey VARCHAR(255) NULL DEFAULT NULL,"
            		+ "secretKey VARCHAR(255) NULL DEFAULT NULL,"
            		+ "region VARCHAR(255) NULL DEFAULT NULL,"
            		+ "UNIQUE INDEX uk_id (accountID),"
            		+ "PRIMARY KEY (num));";
            
            String dynaKeySql = "create table if not exists dynatrace_key ("
            		+ "num INT(11) NOT NULL AUTO_INCREMENT,"
            		+ "environmentID VARCHAR(255) NULL DEFAULT NULL,"
            		+ "environment VARCHAR(255) NULL DEFAULT NULL,"
            		+ "token VARCHAR(255) NULL DEFAULT NULL,"
            		+ "UNIQUE KEY uk_key (environmentID),"
            		+ "PRIMARY KEY (num));";
            		
            String awsIDSql = "create table if not exists awsconfig_id ("
            		+ "FK_key_num INT(11) NULL,"
            		+ "instanceID VARCHAR(100) NOT NULL,"
            		+ "imageID VARCHAR(100) NULL DEFAULT NULL,"
            		+ "publicDns VARCHAR(100) NULL DEFAULT NULL,"
            		+ "privateDns VARCHAR(100) NULL DEFAULT NULL,"
            		+ "instanceType VARCHAR(100) NULL DEFAULT NULL,"
            		+ "architecture VARCHAR(100) NULL DEFAULT NULL,"
            		+ "platform VARCHAR(100) NULL DEFAULT NULL,"
            		+ "region VARCHAR(100) NULL DEFAULT NULL,"
            		+ "isUpdate TINYINT(1) NULL DEFAULT false,"
            		+ "PRIMARY KEY (FK_key_num, instanceID),"
            		+ "CONSTRAINT awsconfig_id_ibfk_2 FOREIGN KEY (FK_key_num) REFERENCES cloudresource.awsconfig_key (num) ON UPDATE CASCADE ON DELETE CASCADE);";
            
            
            String dropProcedure = "DROP PROCEDURE IF EXISTS update_awsconfig_id;";
            
            String creatUpdateAwsIdPSql = 
            		"CREATE PROCEDURE update_awsconfig_id()\n"
            		+ "BEGIN\n"
            		+ "UPDATE awsconfig_id\n"
            		+ "SET isUpdate=1\n"
            		+ "WHERE instanceID IN (SELECT instanceID FROM awsresource WHERE instanceID in (select instanceID from awsconfig_id) AND sources='Dynatrace' GROUP BY instanceID);\n"
            		+ "END";

            
            Statement statement = connection.createStatement();
            
            statement.executeUpdate(adminLogSql);
            statement.executeUpdate(userSql);
            
            statement.executeUpdate(ec2PriceSql);
            statement.executeUpdate(regionCode2NameSql);
            statement.executeUpdate(insertRegionCode2Name);
            
            statement.executeUpdate(awsRowSql);
            statement.executeUpdate(awsDiskRowSql);
            statement.executeUpdate(awsKeySql);
            statement.executeUpdate(dynaKeySql);
            statement.executeUpdate(awsIDSql);
            
            statement.executeUpdate(dropProcedure);
            statement.executeUpdate(creatUpdateAwsIdPSql);
            
        } catch (Exception e){
            System.out.println(e);
        }
    }
}