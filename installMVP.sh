#!/bin/bash
# Installs IntelliJ templates

echo "开始安装插件"

find /Applications -path "*.app" -prune \( -name "*Android Studio*"  \) -print0 | while read -d $'\0' folder
do
  echo "\nInstalling to $folder"
  cp -frv $( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/MVP-PLUGING "$folder/Contents/plugins/android/lib/templates/activities/"
done


echo "安装完成"
echo ""
echo "请重新启动Android Studio"
