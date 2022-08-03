function appendRandomPeopleData(limit) {
                let fakePersons = $('#fakePersons');
                let seedValue = $('#seed').val();
                let errorsPerRecordValue = $('#errorField').val();
                let localeValue = $('#locale').val();
                let pageValue = fakePersons.attr('data-page');

                $.get("/random/persons", {seed: seedValue, errorsPerRecord: errorsPerRecordValue, locale: localeValue, page: pageValue, limit: limit}, data => {
                    let htmlData = "";
                    for (person of data) {
                        let row = "<tr>";
                        for (const [key, value] of Object.entries(person)) {
                            row += "<td>" + value + "</td>";
                        }
                        row += "</tr>";
                        htmlData += row;
                    }
                    fakePersons.data("page", pageValue);
                    if (pageValue == 0) {
                        pageValue = 2;
                    } else {
                        pageValue++;
                    }
                    fakePersons.find('tbody').append(htmlData);
                    fakePersons.attr('data-page', pageValue);
                });
            }

$(document).ready(() => {
            let fakePersons = $('#fakePersons');
            let limit = fakePersons.attr('data-limit');
            let start = fakePersons.attr('data-start');

            appendRandomPeopleData(start);

            let win = $(window);
            let doc = $(document);
            win.scroll(function() {
                if (doc.height() - win.height() == win.scrollTop()) {
                    appendRandomPeopleData(limit);
                }
            });
        });