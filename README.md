# ![ENLIT logo](/src/main/resources/logo.png) ENLIT (Exploring New LITerature)

ENLIT is a tool that supports scholars in exploring new literature:
- It makes **backward searches more efficient** by extracting the references from a literature corpus (set of PDFs) and providing a list without duplicates. It also provides statistics on journals and authors that are cited frequently by the given literature corpus.
- It **implements a new exploratory strategy that facilitates understanding**: simply start by reading the most influential papers and skim the remaining papers afterwards. A paper that explains why this strategy is more effective than the traditional *skim first and read afterwards* approach will be published soon.

## Installation

1. Install the latest version of GROBID, a machine-learning library for bibliographic data. Following the instructions of [GROBID](https://grobid.readthedocs.io/en/latest/Install-Grobid/) will ensure a clean installation of the project.
2. Download the latest [release of ENLIT](https://github.com/WinforUniRegensburg/enlit/releases).
3. When starting ENLIT, set the GROBID path (Menu File, Settings, GROBID path).

## Developing and Contributing

Import ENLIT as a Maven-project.

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request

## Authors

* **Philip Empl** - *University of Regensburg*
* **Gerit Wagner** - *University of Regensburg*

## License

This project is licensed under the [MIT license](LICENSE.md), it is based on [GROBID](https://github.com/kermitt2/grobid) and uses [JabRef](https://github.com/JabRef/jabref) code.

## Cite

Wagner, G., Empl, P., & Schryen, G. (2020, May). Designing a novel strategy for exploring literature corpora. *Proceedings of the European Conference on Information Systems*. https://aisel.aisnet.org/ecis2020_rp/44/
