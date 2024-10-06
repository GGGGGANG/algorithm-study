#!/usr/bin/env bash
# JUnit 5 standalone 러너로 main + test 소스를 한 번에 컴파일하고 실행한다.
# Maven/Gradle 없이 동작하도록 lib/ 의 jar 한 개에만 의존한다.

set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JUNIT_JAR="$ROOT/lib/junit-platform-console-standalone-1.10.2.jar"
BUILD_DIR="$ROOT/build"
MAIN_SRC="$ROOT/src/main/java"
TEST_SRC="$ROOT/src/test/java"

if [[ ! -f "$JUNIT_JAR" ]]; then
  echo "JUnit standalone jar 가 없습니다: $JUNIT_JAR" >&2
  exit 1
fi

rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

# 컴파일: main 과 test 를 모두 같은 build 디렉터리로 출력.
# (bash 3.2 의 macOS 기본 셸에서도 동작하도록 mapfile 대신 단어 분리에 의존한다.)
SOURCES=$(find "$MAIN_SRC" "$TEST_SRC" -name '*.java')
javac -encoding UTF-8 -d "$BUILD_DIR" -cp "$JUNIT_JAR" $SOURCES

# 테스트 실행
java -jar "$JUNIT_JAR" execute --class-path "$BUILD_DIR" --scan-class-path \
  --details=tree --disable-banner
