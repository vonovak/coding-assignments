## Faceted Search Demo

This is a simple demo of faceted search implemented using ElasticSearch.

To run, download elasticsearch 1.7.4 and run it (go to bin folder and run `./elasticsearch`).
Then go to the folder where this app is clonned and run `./grailsw run-app`.

Elasticsearch is set up to automatically index the database on startup, so before runnin in again, you should remove the `data` folder in elasticsearch root or switch the `bulkIndexOnStartup` to false in `Config.groovy`.


<img src="https://raw.githubusercontent.com/vonovak/coding-assignments/assignment/faceted-search/demo.png" width="750">
