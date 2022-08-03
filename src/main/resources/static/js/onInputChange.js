function onInputChange() {
setTimeout(function(){
    let fakePersons = $('#fakePersons');
    let fakePersonsBody = fakePersons.find('tbody').html("");
    let pageValue = fakePersons.attr('data-page');
    let limit = fakePersons.attr('data-limit');
    let start = fakePersons.attr('data-start');
    fakePersons.data('data-page', pageValue);
    pageValue = 0;
    fakePersons.attr('data-page', pageValue);
    $.getScript("InfiniteScroll.js", appendRandomPeopleData(start));
    }, 500)
    }
$(document).on("input", function(){
    onInputChange();
});