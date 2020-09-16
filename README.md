# `anoscan` - Java Annotated Scanner

## Introduction
Simplifies talking input from console.

! Maybe redundant. Better to follow JSR 303 style
@ScanCmd: Can be applied to only class. If provided will scan all properties

! Better design is like JSR 303. Each class need to have these properties
@ScanMeta: Can be applied to field.
    - prompt: Message to be printed in System.in. Support JRS 303 style message key with {}
    - group: default empty, can specify multiple group
    - order: default -1, specify order of scan
    - noPrompt: default false

@ScanOrder: Can be applied to class(!) or method. If applied to method, expected to return Collection<String> or String[]
            Method parameter can have List<Group>
    - values:

@PreScan, @PostScan: Can be applied to method. Can inject System.in, System.out, PropertyReader
                     Also has support for injecting custom Object using predefined interfacd

ScanManager: Class like PersistantManager
```java
ScanManager.process(Person.class, System.in, System.out, Collection<String>|String[] order)
ScanManager.registerArgument(Test.class, new Test()); // Now can inejct Test in @ScanPre, @ScanPost
```

@ScanProcessor: Can specify custom processor. Each processor need to implement ScanProcessor? interface
    - value

! Useless if @ScanCmd is dropped
@ScanIgnore: Ignore when @ScanCommand is specified.

@ScanGroup - Method level annotation. If given to a method will execute for each method argument
    - name: default will take method name
    - prompt: Message to be printed in System.in. Support JRS 303 style message key with {}
    - group: default empty, can specify multiple group
    - order: default -1, specify order of scan
    - skipPrompt

Scan order can be provided in various way. Following is the priority
    1. ScanManager method argument
    2. @ScanOrder in method
    3. @ScanOrder in class
    4. order @Scan
If can not determine order will through exception

When @Scan annotation is given,
    - First search for @ScanProcessor
    - Then Search for annotation, annotated with @ScanProcessor
    - Then will try to predict
Throw exception if not processor found

@ScanDelimiter - Will provide a way to supply delimiter to underlying Scanner. Class level annotation

Will have annotation for all default scan type
@ScanInt
@ScanLong
@ScanLine

Some predefined custom processor
! Need to think a way to pass custom processor data to ScanProcessor interface
@ScanMultiLine - Will take input empty line is not given
@ScanFixedLine - Will input n lines
@ScanDate - Input directly to date object
    - format

