/* global require, module */

var EmberApp = require('ember-cli/lib/broccoli/ember-app');

var app = new EmberApp({
  'ember-cli-bootstrap': {
    'importBootstrapJS': true
  }
})

// Use `app.import` to add additional libraries to the generated
// output files.
//
// If you need to use different assets in different
// environments, specify an object as the first parameter. That
// object's keys should be the environment name and the values
// should be the asset to use in that environment.
//
// If the library that you are including contains AMD or ES6
// modules that you would like to import into your application
// please specify an object with the list of modules as keys
// along with the exports of each module as its value.

// bootstrap
app.import('bower_components/bootstrap/dist/js/bootstrap.js')
app.import('bower_components/bootstrap/dist/css/bootstrap.css')
app.import('bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.woff2', {
	destDir: "fonts"
})

//d3
app.import('bower_components/d3/d3.js')

//jstree
app.import('bower_components/jstree/dist/jstree.js')
app.import('bower_components/jstree/dist/themes/default/style.css')

app.import('bower_components/jstree/dist/themes/default/throbber.gif', {
	destDir: 'assets'
})
app.import('bower_components/jstree/dist/themes/default/32px.png', {
	destDir: 'assets'
})
app.import('bower_components/jstree/dist/themes/default/40px.png', {
	destDir: 'assets'
})

//datatables
app.import('bower_components/datatables/media/js/jquery.dataTables.js')
app.import('bower_components/datatables/media/css/jquery.dataTables.css')
app.import('bower_components/datatables/media/css/jquery.dataTables_themeroller.css')
app.import('bower_components/datatables/media/images/back_disabled.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/back_enabled_hover.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/back_enabled.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/favicon.ico', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/forward_disabled.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/forward_enabled_hover.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/forward_enabled.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/sort_asc_disabled.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/sort_asc.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/sort_both.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/sort_desc_disabled.png', {
	destDir: 'images'
})
app.import('bower_components/datatables/media/images/sort_desc.png', {
	destDir: 'images'
})

//highcharts
app.import('bower_components/highcharts-release/highcharts.src.js')
app.import('bower_components/highcharts-release/highcharts-more.src.js')

//highlight
app.import('vendor/jquery.highlight.js')

app.import("vendor/jquery-ui/jquery-ui.js")
app.import("vendor/jquery-ui/jquery-ui.css")
app.import("vendor/jquery-ui/jquery-ui.structure.css")
app.import("vendor/jquery-ui/jquery-ui.theme.css")
app.import("vendor/jquery-ui/images/ui-bg_flat_0_aaaaaa_40x100.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_flat_75_ffffff_40x100.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_glass_55_fbf9ee_1x400.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_glass_65_ffffff_1x400.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_glass_75_dadada_1x400.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_glass_75_e6e6e6_1x400.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_glass_95_fef1ec_1x400.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-bg_highlight-soft_75_cccccc_1x100.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-icons_222222_256x240.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-icons_2e83ff_256x240.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-icons_454545_256x240.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-icons_888888_256x240.png", {
	destDir: 'assets/images'
})
app.import("vendor/jquery-ui/images/ui-icons_cd0a0a_256x240.png", {
	destDir: 'assets/images'
})

module.exports = app.toTree();
