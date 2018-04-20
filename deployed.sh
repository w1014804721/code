#!/usr/bin/env bash
scp -r ./target/code/WEB-INF/classes/* root@wangjingxin.top:/root/apache-tomcat-8.5.30/webapps/ROOT/WEB-INF/classes/
#scp -r ./target/code/* root@wangjingxin.top:/root/apache-tomcat-8.5.30/webapps/ROOT/