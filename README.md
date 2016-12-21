# photorenamer

PhotoRenamer is an application that allows a user to easily rename image files based on a set of tags. The user can choose a directory and view a list of all image files anywhere under that directory. When viewing an image, the user can select tags from the currently-existing tags, and the user can also add new tags and delete existing ones from the currently-existing ones. The application will rename the image file to include the tags, each prefixed with the "@" character. For example, if the user has tagged an image with "Aunt June" and "Samantha", then the file will be renamed to include "@Aunt June @Samantha". This allows the user to use their operating system to search for image files. The user should also be able to open (in their OS) the directory containing the current image file.

Provided that an image has not been moved to a different location or manually renamed using the OS, users can view all the names that a file has had. For example, if the user views the image with Aunt June and Samantha, they can see both the original name and the name that includes "@Aunt June @Samantha". The user can choose to go back to an older name.

The list of available tags persists when the application is quit and reopened.

The system keeps a log of all renaming ever done (old name, new name, and timestamp), and the user can view this log.

When PhotoRenamer is first run, it creates any configuration files that it needs, and if they are deleted it recreates them the next time it is run.
