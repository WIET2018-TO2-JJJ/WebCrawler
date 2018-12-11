# WebCrawler
The desktop application used for searching specified websites based on made query.


## Query Language

|Rule|Description|
|:---:|---|
| `*` | Non-greedy wildcard |
| `<N>` | Match `N` words |
| `word` | Match specific `word` |
| <code>(rule1 &#124; rule2)</code> | Match `rule1` or `rule2` | 

### Examples

##### Sentence with 1 or 3 words

<code>(\<1\> &#124; \<3\>)</code>
 
##### Sentence starting with at least 3 words.

<code>* \<3\></code>

##### Sentence containing 2 specific words in any order and at any position in sentence

<code>* (home &#124; rome &#124; dome) * (home &#124; rome &#124; dome)  *</code>

will match against

`My home in Rome has dome.`<br>
`Rome has home with best dome.`

#### Other

<code>\<2\> root \<3\> </code>

will match against

`Money is root of all evil.`

=======
## Query language
\*    - anything <br/>
text  - search for word 'text' : Letter case sensitive <br/>
\<N>  - match N words <br/>
|     - logical OR <br/>
()    - brackets <br/>
  
Example: <5> (word1 | word2) means match any five words and then must be word1 OR word2


### License
Icons made by [Freepik](https://www.freepik.com) from [Flaticon](https://www.flaticon.com/) 
is licensed by [Creative Commons BY 3.0](http://creativecommons.org/licenses/by/3.0/)
