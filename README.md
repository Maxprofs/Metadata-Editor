# Metadata-Editor

This desktop application is usable to edit the metadata properties of a video file in windows. The workflow behaves in this way:

1. The application asks the user for an Excel Spreadsheet with the video's information that the user wants to modify

    - In order for this software to understand the tags (System.keywords), the user in the excel spreadsheet should use (;) instead of (,).

    - The format accepted by this software on the spreadsheet is the following:

        - VIDEO NAME | TITLE | SUBTITLES | RATING | TAGS | COMMENTS | AUTHOR URL | PROMOTION URL

        - It is strongly recommended to use this exact same format (exact same letters as well), in order for this software to run accordingly.
        
    - It accepts `.xls` and `.xlsx` spreadsheet formats.
    
2. Once the application succesfully loads the spreadsheet, it will ask the user for a location on where to find the videos specified on the spreadsheet.

3. When the location is finally specified, the application will look for the videos and will sequentiely load each video on memory, showing the current video meta data property. The user can edit the meta data information taken from the spreadshet in the application as well.

4. Finally the application applys changes to the video meta data properties.

There is a small C++ application that works behind the JAVA application, which is the called ShellProperty developed by Matthew van Erde, this small app can be found in this link:  https://blogs.msdn.microsoft.com/matthew_van_eerde/2013/09/24/shellproperty-exe-v2-read-all-properties-on-a-file-set-properties-of-certain-non-vt_lpwstr-types/ . This C++ application makes the real work, it is the one which goes into the video files and use a C++ library called Active Template Library (ATL), to get and modify all the meta data properties that a video file in Windows OS is able to show.
    

## Setup

The source code was built in NetBeans, so, in order to make changes, you need to use this IDE. To compile the code correctly, make sure you have the following libraries:

    - poi-ooxml-3.9.jar
    - poi-3.9.jar
    - xmlbeans-2.3.0.jar
    - poi-3.5-ooxml-schemas-1.0.jar
    - dom4j.jar
    
If you need help, do not hesitate to contact me directly at any time =D
