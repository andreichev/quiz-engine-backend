<div class="title">${entity}</div>

<#foreach triple in triples>
<div class="text">What is the ${triple.predicateLabel!} of ${triple.subjectLabel!}? (${triple.objectLabel!})</div>
</#foreach>