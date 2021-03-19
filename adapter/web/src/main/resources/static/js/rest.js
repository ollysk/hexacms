function unpublish(node, el) {
  el.style.setProperty("text-decoration", "line-through");
    url2 = 'http://localhost:8080/api/admin/nodes/'+node;
    // node.nodeMeta.isPublished = false;
    // node.title="sdfgdfg";
    // delete node.id;
    // delete node.categories;
    // delete node.countries;
    fetch(url2, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/merge-patch+json'
        },
        //body: JSON.stringify({foo: "This", node: node})});
        body: JSON.stringify({"isPublished":false})});
}