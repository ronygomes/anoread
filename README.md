# anoread - Java Annotated Reader

Simplifies talking input from console.

Suppose want to take input in following class:
```java
public class Person {
    private String name;
    private int age;
    private List<String> favouriteLanguage;

    // getter and setter
    
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", favouriteLanguage=" + favouriteLanguage +
                '}';
    }
}
```

With `Anoread`, you just have to call a single method.
```java
public class Main {
    public static void main(String[] args) {
        Person p = Anoread.readAndGet(Person.class, "name", "age", "favouriteLanguage")

        System.out.println(p);
    }
}
```

Running `Main` will promt for input. Entering input will automatically bind in `Person`
```bash
$ javac Main
Enter name: John
Enter age: 25
Enter favouriteLanguage: Java, C++, Go

Person{name='John', age=25, favouriteLanguage=[Java, C++, Go]}
```

Prompt text can be modified with `@ReadAttributes` annotation:

```java
@ReadAttributes(prompt = "Enter First Name")
private String name;
```

Data can be validated using `@Validator` anotation on following method signature. This method will be called for each input:

```java 
@Validator
void validate(Object value, ReadMeta meta) {
    switch (meta.getName()) {
        // Validate only `age' field
        case "age":
            if (((int) value) > 100) {
                throw new ValidationError("Age can not be greater than 100");
            }
    }
}
```
