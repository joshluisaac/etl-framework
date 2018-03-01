import spock.lang.*

// Hit 'Run Script' below
class TestSpock extends Specification {
    def "let's try this!"() {
        expect:
        Math.max(1, 2) == 3
    }
}