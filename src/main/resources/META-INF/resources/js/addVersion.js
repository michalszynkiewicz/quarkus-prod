function addVersion() {
    let body = {
        id: document.getElementById('id').value,
        baseVersion: document.getElementById('baseVersion').value,
        upstreamVersion: document.getElementById('upstreamVersion').value,
        newest: document.getElementById('newest').checked
    };
    if (!body['baseVersion'].length>0){
        body.baseVersion = "newest"
    }
    fetch('/versions', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    }).then(response => { // TODO handle failure, etc
        if (response.status < 300) {
            window.location.href = '/extensions.html'
        } else {
            console.log('failed to add a new version', response);
            alert('Failed to add version. Please look into console.log for details :)')
        }
    })
}