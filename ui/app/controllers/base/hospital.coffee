`import Ember from 'ember'`

BaseHospitalController = Ember.ObjectController.extend(
  queryParams: ['alias', 'name', 'address']

  alias: null
  name: null
  address: null
  
  editEntity: {}
  
  search: (()->
    t = this
    console.log("observe name, alias, address to search")
    queryCondition = {
      name: t.get("name")
      alias: t.get("alias")
      address: t.get("address")
    }
    if (this.model)
      this.model.set "queryCondition", queryCondition
  ).observes('name', 'alias', 'address', 'model')
  
  selectEntity: (()->
    t = this
    currentSelectedId = t.model.get("currentSelectedId")
    if currentSelectedId
      entity = t.model.getById(t.model.get("currentSelectedId"))
      if entity
        console.log("selected: " + currentSelectedId + ", entity: " + JSON.stringify(entity))
        t.set "editEntity", entity
  ).observes("model.currentSelectedId")
  
  actions:
    deleteSelected: ()->
      t = this
      console.log("deleteSelected button: " + JSON.stringify(this.get("selected")))
      currentSelectedId = t.model.get("currentSelectedId")
      for selectedId in this.get("selected")
        if currentSelectedId == "" + selectedId
          t.set "editEntity", {}
      t.model.delete(this.get("selected")).then ()->
        t.search()
    new: ()->
      t = this
      console.log("new entity!")
      e = t.get("editEntity")
      if e.name
        e.id = null
        t.model.save(e).then ()->
          t.set "editEntity", {}
          t.search()
    save: ()->
      t = this
      console.log("save hospital: " + JSON.stringify(t.get("editEntity")))
      t.model.save(t.get("editEntity")).then ()->
        t.search()
)
`export default BaseHospitalController`