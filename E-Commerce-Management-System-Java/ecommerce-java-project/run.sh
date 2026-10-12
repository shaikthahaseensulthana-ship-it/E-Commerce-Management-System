#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac -encoding UTF-8 -d out $(find src/main/java -name '*.java')
java -Dfile.encoding=UTF-8 -cp out com.ecommerce.main.ECommerceApplication
