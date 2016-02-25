`import Ember from 'ember'`
`import config from '../config/environment'`

BaseModel = Ember.Object.extend({
  results: null
  
  currentSelectedId: null
  selected: []
  
  queryCondition: null
  
  getById: (id)->
    t = this
    if t.get("results")
      for record in t.get("results").result
        if id == "" + record.id
          return record
    return null
  search: (queryCondition, page, orderCondition)->
    t = this
    console.log("search in hospital model, name: " + queryCondition.name + ", alias: " + queryCondition.alias + ", address: " + queryCondition.address)
    url = config.jajbUrl + "rest/" + t.get("entityName") + "s/search" + "?start=" + page.start + "&limit=" + page.limit
    if orderCondition && orderCondition.name && orderCondition.sort
      url += "&order=" + orderCondition.name + "&sort=" + orderCondition.sort
    ajax(url, {
      type: "POST"
      data: JSON.stringify(queryCondition)
    }).then (result)->
      console.log(result[0])
      t.set("results", result[0])
      return result[0]
  
  save: (entity)->
    t = this
    if entity
      console.log("save " + t.get("entityName") + ", entity: " + JSON.stringify(entity))
      method = "POST"
      url = config.jajbUrl + "rest/" + t.get("entityName") + "s"
      if entity.id
        method = "PUT"
        url += "/" + entity.id
        
      ajax(url, {
        type: method
        data: JSON.stringify(entity)
      })
      
  delete: (ids)->
    t = this
    if ids
      console.log("delete ids for " + t.get("entityName") + ", ids=" + JSON.stringify(ids))
      url = config.jajbUrl + "/rest/" + t.get("entityName") + "s?"
      for id in ids
        url += "ids=" + id + "&"
      ajax(url, {
        type: "DELETE"
      })
}
)
`export default BaseModel`
