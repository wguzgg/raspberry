`import Ember from 'ember'`
`import config from './config/environment'`

# wraps the jQuery ajax call to be PromiseA+ compatible promise object
# see the Ember author's blog why jQuery's promise is broken: http://domenic.me/2012/10/14/youre-missing-the-point-of-promises/
window.ajax = (args...)-> Ember.RSVP.all [$.ajax.apply($, args)]

Router = Ember.Router.extend {
  location: config.locationType
}

Router.map ()->
  this.resource 'base', {path: '/base'}, ()->
    this.route 'hospital', {path: 'hospital'}
    
`export default Router`
