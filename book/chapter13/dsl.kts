"https://api.sampleapis.com/codingresources/codingResources/{id}" get {
    path name "id" value "1"
    query name "q" value "v1"
    query name "q" value "v2"

    execute {
        println("status = ${this.status}, content = ${this.content}") //status = 200, content = OK
    }
}


/**
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 * ========================================================================================================
 */


@DslMarker // 암묵적 외부 리시버 탐색을 제한한다 (명시적 접근은 허용)
annotation class XDslMarker

interface K {
    infix fun value(value: String): Unit
}

@XDslMarker
class PathBuilder {
    private val pathMap = mutableMapOf<String, String>()

    infix fun name(name: String): K {
        return object : K {
            override fun value(value: String) {
                pathMap.put(name, value)
            }
        }
    }

    fun build(uri: String): String {
        var _uri = uri
        for (path in pathMap) {
            _uri = _uri.replace("{${path.key}}", path.value)
        }
        return _uri
    }
}

@XDslMarker
class QueryBuilder {
    private val queryMap = mutableMapOf<String, MutableList<String>>()

    infix fun name(name: String): K {
        return object : K {
            override fun value(value: String) {
                queryMap.getOrPut(name, { mutableListOf<String>() }).add(value)
            }
        }
    }

    fun build(): String {
        val sb = StringBuilder("?")
        for (query in queryMap) {
            sb.append("${query.key}=${query.value.joinToString()}&")
        }
        return sb.toString()
    }
}

class Result(val status: Int, val content: String)

@XDslMarker
class GetBuilder(val url: String) {
    val path = PathBuilder()
    val query = QueryBuilder()

    infix fun execute(handler: Result.() -> Unit) {
        val result = call()
        handler(result)
    }

    private fun call(): Result {
        var uri = path.build(url) + query.build()
        println("call ===> $uri")
        return Result(200, "OK")
    }
}

infix fun String.get(builder: GetBuilder.() -> Unit): Unit = builder(GetBuilder(this))
