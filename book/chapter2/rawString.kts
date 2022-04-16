fun memo(name: String) = """
    $name 에게
    코틀린은 멀티라인 문자열 작성 기능을 제공합니다
    trimMargin() 메서드를 이용하여 문자열 Margin 을 제어할 수 있습니다.
    책에선 trimMargin() 을 소개하고 있지만, idea 에서 추천한 trimIndent() 를 이용해 보았습니다.
    trimIndent() 는 기본 인덴트를 감지합니다
""".trimIndent()

println(memo("박제희"))
