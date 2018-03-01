package demo

/**
 * Created by joshua on 5/14/17.
 */
class HelloTest extends spock.lang.Specification {
    def "SayHello"() {
        expect: hello == "Hello, Gradle"
        where: hello = new Hello().sayHello()
    }
}
