`import Ember from 'ember'`

SearchResultComponent = Ember.Component.extend({

  initTable: (()->
    t = this
    console.log("!!!init table for search result!!!")
    table = this.$(".search-result")
    table.dataTable {
      searching: false
      columns: t.model.get("columns")
      bServerSide: true
      ajax: (data, callback, settings)->
        t.model.set "selected", []
        if t.model
          orderCondition = {}
          if data["order"].length > 0
            index = data["order"][0].column
            orderCondition["name"] = data["columns"][index].name
            orderCondition["sort"] = data["order"][0].dir
          page = {
            start: data.start
            limit: data.length
          }
          console.log("load data for search result table, condition is " + JSON.stringify(t.model.get("queryCondition")))
          t.model.search(t.model.get("queryCondition"), page, orderCondition).then (result)->
            for record in result.result
              record["DT_RowId"] = record.id
            data = {
              aaData: result.result
              iTotalRecords: result.count
              iTotalDisplayRecords: result.count
            }
            callback(data)
        else
          console.log("no model, can't load data for search result table")
    }
    table.DataTable().on "draw", ()->
      body = $(table.DataTable().table().body())
      body.unhighlight()
      queryString = t.model.get("queryCondition")
      for key, value of queryString
        if value
          body.highlight(value)
    t.$(".search-result tbody").on "click", "tr", (e)->
      $(e.currentTarget).toggleClass("highlight")
      
      id = $(e.currentTarget).attr("id")
      console.log("select record, id: " + id)
      t.model.get("selected").push id
      t.model.set "currentSelectedId", id
  ).on("didInsertElement")
  refresh: (()->
    t = this
    console.log("refresh search result")
    t.$(".search-result").DataTable().ajax.reload(null, false)
  ).observes("model.queryCondition")
}
)
`export default SearchResultComponent`