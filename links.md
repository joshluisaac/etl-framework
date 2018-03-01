

#Links

[Stackoverflow](https://stackoverflow.com/questions/33106391/how-to-check-if-list-is-empty-using-thymeleaf/33106495 "Thymeleaf")

[Stackoverflow](https://stackoverflow.com/questions/43484142/how-to-print-array-size-in-thymeleaf)

[Stackoverflow](https://javabeat.net/expression-language-thymeleaf/)

[Stackoverflow](https://www.bincsoft.com/blog/thymeleaf-and-lists-in-forms/)

[Stackoverflow](https://stackoverflow.com/questions/26049708/spring-thymleaf-and-lists-of-strings)

[How to serve static html content page in spring-boot](https://stackoverflow.com/questions/31876389/how-to-serve-static-html-content-page-in-spring-boot)

[Spring, Thymleaf and lists of strings](https://stackoverflow.com/questions/26049708/spring-thymleaf-and-lists-of-strings)


###checking for nulls in thymleaf
[Stackoverflow checking for nulls](https://stackoverflow.com/questions/20636456/using-thymeleaf-when-the-value-is-null)

**Elegant solution**

``<td th:text="${user?.address?.city}"></td>``

The ``?.`` operator is called the "safe navigation" operator, per the Spring Expression Language docs

Other methods

``<span th:text="${someObject.someProperty != null} ? ${someObject.someProperty} : 'null value!'">someValue</span>``

``<span th:text="${someObject.someProperty != null} ? ${someObject.someProperty}">someValue</span>``


Expression languages
https://github.com/mvel/mvel