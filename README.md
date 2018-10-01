# Earley-Parser
An implementation of the Earley Parser in Java. This was completed as part of the Formal Models of Languages course for my second year at Cambridge University.

The Earley Parser is able to parse strings that are part of a context-free grammar in _O(n^3)_. It works by maintaining a table of previous, future and current states in parsing the string. As the parser traverses down the table it can do one of three things on a given entry:

- _Predict_: If the next symbol in the input stream is a non-terminal, it will add new entries to the table that will try and derive that non-terminal
- _Scan_: If the next symbol in the input stream is a terminal, it will try and consume that terminal if the current derivation permits it.
- _Complete_: Once a derivation of a non-terminal has been completed we use this derivation to resolve previous uncompleted derivations where this non-terminal is the next item to be derived
