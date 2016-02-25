`import Ember from 'ember'`
`import HospitalModel from '../../models/hospital'`

BaseHospitalRoute = Ember.Route.extend(
  model: (params)->
    console.log("create model for hospital in hospital route")
    model = HospitalModel.create()
    return model
    
  setupController: (controller, model)->
    this._super(controller, model)
    console.log("setup controller in hospital route")
    controller.set "model", model
)
`export default BaseHospitalRoute`
