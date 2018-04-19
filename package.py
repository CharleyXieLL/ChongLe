# coding=utf-8
import os
import sys

reload(sys)

packageType = "dev";#默认打包jiajia测试版

#遍历参数，根据参数的不同来打出不同的包
for i in range(1,len(sys.argv)):
  if(sys.argv[i].find("dev") != -1):
    packageType = "dev";
  if(sys.argv[i].find("release") != -1):
    packageType = "release";

if packageType == "dev":
  print "即将打包测试版  packageType = ",packageType
  print "开始执行打包命令  ./gradlew clean assembleDebug"
  os.system("./gradlew clean assembleDebug")
if packageType == "release":
  print "即将打包正式版  packageType = ",packageType
  print "开始执行打包命令  ./gradlew clean assembleRelease"
  os.system("./gradlew clean assembleRelease")


