# Metadata-Editor

This desktop application is usable to edit the metadata properties of a video file in windows. The workflow behaves in this way:

1. The application asks the user for an Excel Spreadsheet with the video's information that the user wants to modify

    - In order for this software to understand the tags (System.keywords), the user in the excel spreadsheet should use (;) instead of (,).

    - The format accepted by this software on the spreadsheet is the following:

        - VIDEO NAME|TITLE|SUBTITLES|RATING|TAGS|COMMENTS|AUTHOR URL|PROMOTION URL

        - It is strongly recommended to use this exact same format (exact same letters as well), in order for this software to run accordingly.
        
    - It accepts `.xls` and `.xlsx` spreadsheet formats.
    
2. Once the application succesfully loads the spreadsheet, it will ask the user for a location on where to find the videos specified on the spreadsheet.

3. When the location is finally specified, the application will look for the videos and will sequentiely load each video on memory, showing the current video meta data property. The user can edit the meta data information taken from the spreadshet in the application as well.

4. Finally the application applys changes to the video meta data properties.

There is a small C++ application that works behind the JAVA application, which is the called ShellProperty developed by Matthew van Erde, this small app can be found here:

    - https://blogs.msdn.microsoft.com/matthew_van_eerde/2013/09/24/shellproperty-exe-v2-read-all-properties-on-a-file-set-properties-of-certain-non-vt_lpwstr-types/
    
    