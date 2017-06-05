(function (window) {
    'use strict';
    // Your starting point. Enjoy the ride!
    window.onload = function () {
        $(document).ready(function () {
            getTodoList();
            insertTodo();
            filterMode();
            deleteTodo();
            deleteByCompleted();
            toggle();
        });
    };

    function getCountActive() {
        $.ajax({
          type: 'GET',
          url: 'api/todos',
          success: function (response) {
              $('.todo-count > strong').text(response);
          },
          error: function (request, status, error) {
              alert("code:" + request.status + "\n" + "message:" +
                  request.responseText + "\n" + "error:" + error);
          }
        });//ajax end
      }//getCountActive() end

    function getTodoList() {
    	var restURL = '';

    	switch(filter.getFilter()) {
	    	case "all" :
	    		restURL = '/all';
	    		break;
	    	case "active" :
	    		restURL ='/active';
	    		break;
	    	case "completed" :
	    		restURL ='/completed';
	    		break;
	    	default:
	    			restURL ='/all';
	    		break;
    	}
        $('.todo-list').empty();
        $.ajax({
            type: 'GET',
            url: '/api/todos' + restURL,
            success: function (response) {
                var liClass;
                var checkedLine;

                getCountActive();
                for (var i = 0; response.length; i++) {
                    if (response[i].completed === 1) {
                        liClass = ' class= "completed" ';
                        checkedLine = ' checked';
                    } else {
                        liClass = "";
                        checkedLine = "";
                    }
                    $('.todo-list').append(
                        "<li id='" + response[i].id + "'" + liClass + ">" +
                        "<div class='view'>" +
                        "<input class='toggle' type='checkbox'" + checkedLine + ">" +
                        "<label>" + response[i].todo + "</label>" +
                        "<button class='destroy'></button> " +
                        "</div>" +
                        "</li>"
                    );
                }//for()
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" +
                    request.responseText + "\n" + "error:" + error);
            }
        });//ajax end
    }//getTodoList() end


    //filter
    var filter = {
        show: "all",
        getFilter: function () {
            return this.show;
        },
        setFilter: function (filter) {
            this.show = filter;
        }
    };

    function filterMode() {
        $(document).on('click', 'a', function (event) {
            event.preventDefault();
            if (this.id === "all") {
                filter.setFilter("all");
                $('a').removeClass('selected');
                $(this).attr('class', 'selected');

                getTodoList();
            } else if (this.id === "completed") {
                filter.setFilter("completed");
                $('a').removeClass('selected');
                $(this).attr('class', 'selected');
                getTodoList();
            } else {
                filter.setFilter("active");
                $('a').removeClass('selected');
                $(this).attr('class', 'selected');
                getTodoList();
            }
        });
    }//filterMode() end

    function insertTodo() {
        $('.new-todo').on('keypress', function (e) {
            if (e.which === 13) {
                var new_todo = $('.new-todo').val().trim();
                if (new_todo === "") {
                    alert("Input your plan.");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: '/api/todos',
                        contentType: "application/json",
                        dataType: 'json',
                        data: JSON.stringify({
                            todo: new_todo,
                            completed: 0
                        }),
                        success: function (response) {
                            getTodoList();
                            $('.new-todo').val("");

                        },
                        error: function (request, status, error) {
                            alert("code:" + request.status + "\n" + "message:" +
                                request.responseText + "\n" + "error:" + error);
                        }
                    });//ajax end
                }//else end
            }
        });
    }//insertTodo() end

    function toggle() {
        $(document).on("click", ".toggle", function (event) {
            var li_tag = $(this).parent().parent();
            var li_Id = li_tag.attr('id');
            var completed;
            if ($(this).is(':checked')) {
                completed = 1;
            } else {
                completed = 0;
            }

            $.ajax({
                type: 'PUT',
                url: '/api/todos/' + li_Id,
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    completed: completed
                }),
                success: function () {
                    getTodoList();
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" +
                        request.responseText + "\n" + "error:" + error);
                }

            });//ajax end
        });
    }//toggle() end

    function deleteTodo() {
        $(document).on("click", ".destroy", function (event) {
            var li_tag = $(this).parent().parent();
            var li_Id = li_tag.attr('id');

            $.ajax({
                type: 'DELETE',
                url: 'api/todos/' + li_Id,
                dataType: 'json',
                data: JSON.stringify({
                    id: li_Id
                }),
                success: function () {
                	getTodoList();
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" +
                        request.responseText + "\n" + "error:" + error);
                }
            });//ajax end
        });

    }//deleteTodo end

    function deleteByCompleted() {
        $(document).on("click", '.clear-completed', function () {
            var completed = 1;

            $.ajax({
                type: 'DELETE',
                url: 'api/todos',
                dataType: 'json',
                data: JSON.stringify({
                    completed: completed
                }),
                success: function () {
                    getTodoList();
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" +
                        request.responseText + "\n" + "error:" + error);
                }
            });//ajax end
        });
    }//deleteByCompleted end
})(window);
