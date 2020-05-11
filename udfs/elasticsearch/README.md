# Elasticsearch yauaa Ingest Processor

Explain the use case of this processor in a TLDR fashion.

## Usage


```
PUT _ingest/pipeline/yauaa-pipeline
{
  "description": "A pipeline to do whatever",
  "processors": [
    {
      "yauaa" : {
        "field" : "my_field"
      }
    }
  ]
}

PUT /my-index/my-type/1?pipeline=yauaa-pipeline
{
  "my_field" : "Some content"
}

GET /my-index/my-type/1
{
  "my_field" : "Some content"
  "potentially_enriched_field": "potentially_enriched_value"
}
```

## Configuration

| Parameter | Use |
| --- | --- |
| some.setting   | Configure x |
| other.setting  | Configure y |

## Setup

In order to install this plugin, you need to create a zip distribution first by running

```bash
gradle clean check
```

This will produce a zip file in `build/distributions`.

After building the zip file, you can install it like this

```bash
bin/elasticsearch-plugin install file:///path/to/ingest-yauaa/build/distribution/ingest-yauaa-0.0.1-SNAPSHOT.zip
```

## Bugs & TODO

* There are always bugs
* and todos...


License
=======

    Yet Another UserAgent Analyzer
    Copyright (C) 2013-2020 Niels Basjes

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
