```shell
# kotlin style을 idea 설정 파일에 적용
./gradlew ktlintApplyToIdea

# 커밋 훅 추가 (ktlintCheck task 실행)
./gradlew addKtlintCheckGitPreCommitHook

# 커밋 훅 추가 (ktlintFormat task 실행)
./gradlew addKtlintFormatGitPreCommitHook
```
