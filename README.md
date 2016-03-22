
    For getting URLs shorter we can use bijective function which map id of source URL element to symbols from some alphabet,
for instance we can choose alphabet as [a-zA-Z0-9]. To do generation process in parallel we can use some pool of JVMs
as generator nodes. Our ids can be limited for first time for example with Long.MAX_VALUE, so we can split this range
to the number of nodes (or to fixed ranges with possibility to add nodes), so each node will use unuqie subrange of
numbers thus generating unique short values for shortened URLs. We assume that there will be another cluster of worker
nodes which will return shortened URLs for users. Nodes from that cluster will submit new tasks for generation through
some clustered Queue Server like Rabbit. There will be a couple of arbitrators behind the Rabbit (To avoid Rabbit to be
a single point of failure we can use its mirrored queues). They will arrange those generation tasks to the nodes with
some appropriate algorithm (or simply successively). So at the worker perspective it will be simple task submitting
for generation and simple getting of the result throung same way (listerning some queue). For storing data worker node
can use some distributed key/value storage like cassandra. Key will be an ID and value is pair of source
and shortened URLs. For speed up getting of already requested URLs we can use local caches. Also we can add timestamp
field and use it to verify live policy (or period from last access, but in this case there is a need to update last
accessed time). By the way some storages have a built in functionality for elements ttl.

        So we have the following components:
1) REST worker nodes cluster with balancer - accepts shortened requests, checks already created, submits generation
tasks, listens for shortened URL, storing keys and values to storage and responds results to users
2) rabbit cluster with arbitrators - covers system messaging with correct load balancing through generator nodes and 
delivering results to worker nodes
3) generator nodes - receives generator tasks requests, verifies local ids pool, generates shortened values and send 
results to the queues
4) clustered key/value storage - hold data, maybe supports of ttl features

Howto build and start:
    1. Run build.cmd
    2. Run target/shortener-1.0.0-SNAPSHOT/bin/shortener(.bat)

Usage:
1.  To get shortened URL do HTTP GET like the following:
    http://localhost:8082/shortener/shortemall?sourceUrl=http://www.vogella.com/

2.  To find source URL by shortened version use:
    http://localhost:8082/shortener/findByShorted?shortedUrl=http://localhost:8082/b