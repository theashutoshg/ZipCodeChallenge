# Zip Code Problem

### BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

[94133,94133] [94200,94299] [94600,94699]

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

### PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

#### NOTES
- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices

#### EXAMPLES:
If the input = [94133,94133] [94200,94299] [94600,94699]
Then the output should be = [94133,94133] [94200,94299] [94600,94699]

If the input = [94133,94133] [94200,94299] [94226,94399]
Then the output should be = [94133,94133] [94200,94399]

### Implementation

For implementation, used below pattern and approach to achieve efficiency and simplicity

- Application solved using spring IOC pattern aka Factory Design patterns.
- JSR-380 annotation based validation using Hibernate Validator and it uses Java 8 `default` method so that we don't need to define in each concrete class
- Spring boot auto configuration management which is easy to manage and configure.


### How to 

### Build
`
mvn clean install
`

### Run
###### Syntax
`
java -jar {jar file path}/{jar filename}.jar -z={space separated zip ranges}
`
###### Example 
`
java -jar target/zipcode-challenge-0.0.1-SNAPSHOT.jar -z="[94133,94133] [94200,94299] [94600,94699]"
`
From Project root location, you can also use below command 

`
java -jar target/zipcode-challenge-0.0.1-SNAPSHOT.jar -z="[94133,94133] [94200,94299] [94600,94699]"
`




##Output

``` EMACS
C:\IdeaProjects\ZipCodeChallenge> java -jar target/zipcode-challenge-0.0.1-SNAPSHOT.jar -z="[94133,94133] [94200,94299] [94226,94399]"

2019-07-20 19:05:33.464  INFO 6772 --- [           main] c.a.w.z.ZipCodeChallengeApplication      : No active profile set, falling back to default profiles: default
2019-07-20 19:05:33.970  INFO 6772 --- [           main] c.a.w.z.ZipCodeChallengeApplication      : Started ZipCodeChallengeApplication in 0.898 seconds (JVM running for 1.215)
2019-07-20 19:05:33.971  INFO 6772 --- [           main] c.a.w.z.ZipCodeChallengeApplication      : Args received count 1
2019-07-20 19:05:33.978  INFO 6772 --- [           main] c.a.w.z.handlers.CommandLineProcessor    : Parsed the values and returning the array params
2019-07-20 19:05:34.035  INFO 6772 --- [           main] c.a.w.z.ZipCodeChallengeApplication      : Consolidated Zip Ranges -  [94133,94133] [94200,94399]
```

##### © Ashutosh Gupta