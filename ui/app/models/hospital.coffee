`import Ember from 'ember'`
`import BaseModel from './base-model'`
`import config from '../config/environment'`

HospitalModel = BaseModel.extend({
  entityName: "hospital"
  
  columns: [
    {
      title: "Name"
      name: "name"
      data: "name"
    }
    {
      title: "Alias"
      name: "alias"
      data: "alias"
    }
    {
      title: "Address"
      name: "address"
      data: "address"
    }
  ]
}
)
`export default HospitalModel`
