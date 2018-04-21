#!/usr/bin/env bash
#scp -r ./target/code/* root@wangjingxin.top:/root/apache-tomcat-8.5.30/webapps/ROOT/
scp -r ./target/code/WEB-INF/classes/* elas@wangjingxin.top:/home/elas/apache-tomcat-8.5.30/webapps/code/WEB-INF/classes/
#scp -r ./target/code/WEB-INF/classes/* utribe@web.utribe.me:/home/tomcat/code/WEB-INF/classes/
