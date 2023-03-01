# Files-converter

## Description

An application that can merge multiple files with structured data into an array in JSON format.

Data merging is done based on the id in the file name: {id}-{...}.ext. If the ids match, it means the data belongs to the same object. Each element of the resulting array is the merged data from files with the same id.

It also downloads all the images specified in the source files and downloads them, grouping them by the id of the source files.

## Configuration

The settings are stored in application.yaml.

#### Required:

```
downloading:
  image:
    download-time-limit-in-millis: 200 - maximum number of milliseconds for downloading the image
    images-output-folder-path: src/main/resources/output/images - folder to save images (relative path)

files:
  input:
    input-folder-path: src/main/resources/input - input files folder (relative path)
  output:
    output-folder-path: src/main/resources/output - output folder to write the result (relative path)
    output-file-name: result.json - output file name with extension
```

#### Optional:

```
tree:
  handling:
    duplicates:
      - tag-to-keep: giata_id - tag to be kept
        tag-to-delete: GiataID - tag to be removed if tag-to-keep exists
    tags-to-remove-from-root: content, result, data, found - tags to be removed from the root (their children will be attached to their parents)
    id-tag: giata_id - tag - id of the element to be added to the root
```
The order of operations on the input data is as follows:

1. Removing specified root tags.
2. Removing specified duplicates.
3. Adding an id based on the specified tag.

If any of the settings are missing, the corresponding stage will be skipped.

## How to run

Run from IDE or manually:

Steps:

1. Run "mvn package" command.
2. Run "java -jar -Dspring.config.location=<path-to-file> converter-jar-with-dependencies.jar" command.

Make sure that the relative paths to files in the application.yaml are consistent with the location of the .jar.

