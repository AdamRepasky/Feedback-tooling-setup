# Feedback-tooling-setup

This serves as brief manual how to setup the evironment used to develop and test the "feedback tools" for BBMRI. 

I have used [VM ware workstation](https://www.vmware.com/products/workstation-player/workstation-player-evaluation.html) to tun virtual instance of [ubuntu-22.04.2-desktop-amd64](https://ubuntu.com/download/desktop) on my host machine with Windows 10. In network adapter settings I have used Bridged network connection with replicated network connection state.

Setup:

0. Install docker on both machines

1. On the ubuntu VM download [beam](https://github.com/samply/beam#development-environment) and start the default development environment by running `./dev/beamdev start`

2. On the ubuntu VM download and run [feedback-hub](https://github.com/AdamRepasky/feedback-hub)

3. On the Windows host machine download the Bridgehead (Teiler) from this repo and start it by running:

`docker-compose up -d login-db`

`docker-compose up -d login`

`docker-compose up -d teiler-root-config teiler-ui teiler-core`

`docker-compose up -d exporter-db`

`docker-compose up -d exporter`   -This I have to discuss with David once more, won't work yet TODO

`docker-compose up -d blaze`

And create profile in Bridgehead UI

4. Use bbmri-fhir-gen and blazectl to generate and upload test data:

`bbmri-fhir-gen.exe patients -n 100`

`blazectl.exe --server http://localhost:8091/fhir upload patients`

6. On the Windows download and run the [Feedback Agent](https://github.com/AdamRepasky/feedback-agent)

Everything should be then set up to try the Feedback addition process.
You should be able to access Feedback Agent UI through the Bridgehead UI, select specimens for feedback addition, then in Feedback Hub link the data with feedback refference.
After that check the Blaze store of the Bridgehead (or Feedback Agent database) for added feedback extension.




