# Web-IQ Assignment
## Overview
A program that takes a Wikipedia page as its input and returns the most important words on the page based on their term frequency-inverse document frequency (TF-IDF) score.

The program was written in Java 17 using Maven.

## Program description
A program that takes a Wikipedia page as its input and computes the most important words on that page. It constructs a corpus of all English-language Wikipedia pages reachable in a given number of steps from the input page, or all such pages reachable within the provided time limit. The Wikipedia pages are parsed using the JSoup library. For each page, the relevant page content is stored and bag-of-words model is constructed. A list of frequent words in the English language (stopwords.txt) is used to filter out common words. After the corpus is constructed, the TF-IDF score is computed for each token on the input page.

The (relative) term frequency of term _t_ in document _d_ is given by:
$$ tf(t,d) = {f_{t,d} \over \sum_{t' \in d} f_{t',d}} $$
Here, $f_{t,d}$ is the total number of occurrences of _t_ in _d_, which is then divided by the total number of tokens in the document.

The inverse document frequency of term _t_ relative to the full corpus of documents _D_ is given by:
$$ idf(t,D) = log{N \over |\\{d \in D : t \in D \\}|} $$
Here, _N_ is the total number of documents in _D_. $|\\{d \in D : t \in D \\}|$ is the number of documents in which term _t_ appears.

The highest-ranking tokens based on their TF-IDF score are then printed.

## Running the program 
* Clone this repository:
```
    $ git clone https://github.com/Vishengel/Web-IQ.git
```
Either:
* Run "mvn package" in the root directory to download dependencies and create WebIQ-1.0-SNAPSHOT-jar-with-dependencies.jar

Or:
* Directly use the provided .jar file with dependencies included WebIQ-1.0-SNAPSHOT-jar-with-dependencies.jar

Then:
* Run the program as follows:
```
     > java -jar target/WebIQ-1.0-SNAPSHOT-jar-with-dependencies.jar [Wikipedia page title] [N maximum steps from starting page] [Max runtime in minutes] [N results to print]
```
e.g.
```
     > java -jar target/WebIQ-1.0-SNAPSHOT-jar-with-dependencies.jar Elephant 2 5 25
```
Prove the page title without the preceding url, i.e. the part that follows /wiki/. Replace spaces by an underscore, e.g. Open-source_intelligence.

Running WebIQ-1.0-SNAPSHOT-jar-with-dependencies.jar without parameters will use "Open-source_intelligence 2 5 25" as default settings.
