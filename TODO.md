[ ] Add Bean Validation Support
```java
@Min(18)
private int age;
```

[ ] Feature to read method
```java
@ReadMethod
public sum(@ReadAttributes(prompt = "Price") float price) {
    this.vat = price * 0.15;
}
```

[ ] Support for reading from SuperClass

[ ] Read Property
```java

class Person {

@ReadField
private String name;

@ReadEmbedded
private Address person;
}
```

[ ] Read promt & hint from property file
```java
@ReadAttributes(prompt = "{name.prompt}")
private String name;
```
