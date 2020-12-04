var alertId = 0;
var header = [{
    id: 'name',
    name: 'Name',
    type: 'label'
}, {
    id: 'productizedSince',
    name: 'Productized Since',
    type: 'label'
}, {
    id: 'supportType',
    name: 'Support type',
    type: 'select',
    values: ['SUPPORT', 'DEV_SUPPORT', 'TECH_PREVIEW']
}, {
    id: 'supportStatus',
    name: 'Support status',
    type: 'select',
    values: ['SUPPORTED', 'UNSUPPORTED', 'TENTATIVE']
}, {
    id: 'notes',
    name: 'Notes',
    type: 'text'
}];

let data;

function addVersion() {
    // TODO
}

function addClasses(element, classes) {
    classes.forEach(
        css => element.classList.add(css)
    )
}

function createSelect(id, column) {
    let result = document.createElement('select');
    result.name = column.id;
    addClasses(result, ["form-control"]);

    column.values.forEach(
        value => {
            result.add(new Option(value, value));
        }
    )
    result.onchange = function () {
        propagateUpdate(id, column.id, result.value);
    }

    return result;
}

function createTextArea(id, column) {
    let result = document.createElement('textarea');
    addClasses(result, ["form-control"]);
    result.setAttribute('q-row-id', id);
    result.setAttribute('q-col-id', column.id);
    result.onchange = function () {
        propagateUpdate(id, column.id, result.value);
    }
    return result;
}

function printAlert(message, time) {
    let currentAlert = ++alertId;
    document.getElementById("alert").innerText = message;
    setTimeout(() => {
        if (currentAlert === alertId) {
            document.getElementById("alert").innerText = '';
        }
    }, time * 1000)
}

function propagateUpdate(rowId, colId, result) {
    let disabler = document.getElementById('disabler');
    disabler.setAttribute('disabled', 'disabled');
    printAlert("saving changes...");
    data.forEach(
        row => {
            if (row.id === rowId) {
                row[colId] = result;
                updateExtension(getVersion(), row)
                    .then(response => {
                        // redrawTable();
                        refetch();
                        disabler.removeAttribute('disabled')

                        if (response.status < 300) {
                            printAlert("changes saved", 1);
                        } else {
                            console.log("failed to save changes", response);
                            printAlert("failed to save changes", 2);
                        }
                    })
            }
        }
    )
}

function sortBy(columnId, ascending) {
    function compare(a, b) {
        let result = a[columnId] < b[columnId] ? -1
            : a[columnId] > b[columnId] ? 1
                : 0;
        return ascending ? result : -result;
    }

    data.sort(compare);
    redrawTable();
}

function createTable(container, headers, data) {
    let theader = document.createElement('thead');
    container.appendChild(theader);

    let header = document.createElement('tr');
    theader.appendChild(header);

    headers.forEach(
        headerCell => {
            let cell = document.createElement('td');
            header.appendChild(cell);
            cell.innerHTML = `${headerCell.name} <i class="ri-sort-asc" onclick="sortBy('${headerCell.id}', true)"></i> <i class="ri-sort-desc" onclick="sortBy('${headerCell.id}', false)"></i>`;
        }
    )

    let tbody = document.createElement('tbody');
    container.appendChild(tbody);

    data.forEach(
        dataRow => {
            let tr = document.createElement('tr');
            tbody.appendChild(tr);
            headers.forEach(
                column => {
                    let td = document.createElement('td');
                    tr.appendChild(td);
                    switch (column.type) {
                        case 'select':
                            let select = createSelect(dataRow.id, column);
                            select.value = dataRow[column.id];
                            td.appendChild(select);
                            break;
                        case 'text':
                            let textArea = createTextArea(dataRow.id, column);
                            textArea.value = dataRow[column.id];
                            td.appendChild(textArea);
                            break;
                        case 'label':
                        default:
                            td.innerText = dataRow[column.id];
                            break;

                    }

                }
            )
        }
    )
}

function redrawTable() {
    let contents = document.getElementsByClassName('extensions').item(0);
    while (contents.lastElementChild) {
        contents.removeChild(contents.lastElementChild);
    }
    createTable(contents, header, data);
}

function getVersion() {
    let query = new URLSearchParams(window.location.search);
    return query.get("version") || "newest";
}


function updateExtension(version, row) {
    return fetch(`/versions/${version}/extensions/${row.id}`, {
        body: JSON.stringify({
                supportStatus: row.supportStatus,
                supportType: row.supportType,
                notes: row.notes
            }
        ),
        headers: {
            'Content-Type': 'application/json'
        },
        method: "POST"
    })
}

function refetch() {
    fetch(`/versions/${getVersion()}/extensions`)
        .then(response => response.json())
        .then(content => data = content)
        .then(redrawTable);
}

function initVersionMenu() {
    let versionList = document.getElementById('version-list');
    let selectedVersion = getVersion();
    fetch('/versions')
        .then(response => response.json())
        .then(content => {
            content.forEach(
                version => {
                    let li = document.createElement('li');
                    versionList.appendChild(li);
                    addClasses(li, ["nav-item"]);

                    let a = document.createElement('a');
                    li.appendChild(a);
                    a.href = `extensions.html?version=${version.id}`;
                    a.text = version.id;
                    if ((version.newest && selectedVersion === 'newest') || selectedVersion === version.id) {
                        addClasses(a, ['nav-link', 'active']);
                    } else {
                        addClasses(a, ['nav-link']);
                    }
                }
            )
        })
}

window.onload =
    function () {
        initVersionMenu();
        // TODO: setting the current version
        refetch();
    };