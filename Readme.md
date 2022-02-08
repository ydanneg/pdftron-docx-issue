## Introduction
PDFNet provides the functionality to replace DOCX template variables while converting to PDF.  
https://www.pdftron.com/documentation/java/guides/generate-via-template/

Since PDFNet version 9.2.0 it is broken to recognize variables with a special characters.  
It is a production blocker for us since we use prefixes to introduce a custom type to a variable. 

For example, we use prefix `%` to recognize variable as a special type 'Signature' where our engine prepares a signature image first and then inserts it using PDFNet as an image.   
This is done by a string reason.

Example: `{{%signature}}`

## Problem
We successfully integrated this approach using PDFNet 9.1.0.  

Since PDFNet version 9.2.0 we can't use this approach anymore.  
Is there any reason to limit variables by no having special characters?


## Instructions to run test
`./gradlew check`

### Result
With PDFNet version 9.2.0 test fails with error
````
Exception:
Message: document layout failed: Exception:
Message: Template error: Template key uses illegal character '%':
Signature1: {{%signature1}}
^~~~~~~~~~~~~~~
Conditional expression: isalnum(str[id_len])
Version      : 9.2.0-91be0af529
Platform     : OSX
Architecture : AMD64
Filename     : Template.cpp
Function     : Lex
Linenumber   : 753

	 Conditional expression: 
	 Filename   : FlowToPDFConversion.cpp
	 Function   : PDF::DocxConversion::Convert()
	 Linenumber : 189
	 Error code : 0

	at com.pdftron.pdf.Convert.OfficeToPdfWithFilter(Native Method)
	at com.pdftron.pdf.Convert.officeToPdf(Convert.java:3175)
````

### Expected result

Switch to branch `9.1.0-no-issue`  (or change dependecny version to 9.1.0) to pass the test.  
Result is written to `outtest/converted.pdf`