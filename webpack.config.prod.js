'use strict';

const path = require('path');
const webpack = require('webpack');
const ManifestPlugin = require('webpack-manifest-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
};

const config = {
    mode: 'production',
    entry: './build/js/app.js',

    // TODO: Probably want pollyfills for production...???
    // In production, we only want to load the polyfills and the app code.
    //entry: [require.resolve('./polyfills'), kotlinModuleName],

    // If we do the code splitting we take all of our vendor code and put it in a separate bundle (vendor.min.js)
    // this way it will have better caching/cache hits since it changes infrequently (from https://gist.github.com/gricard/e8057f7de1029f9036a990af95c62ba8)
    // entry: {
    //     vendor: [
    //         // local packages
    //         'react',
    //         'react-dom',
    //         // 'kotlin',
    //         '@material-ui/core'
    //         // npm packages are added to vendor code separately in splitChunks config below
    //     ]
    // },

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js',
    },
    devtool: 'source-map',
    devServer: {
        contentBase: './dist',
        compress: true,
        watchContentBase: true,
    },
    resolve: {
        modules: [
            "js",
            "build/js",
            "resources",
            "node_modules",
            "../../build/web/",
            "../../build/classes/kotlin/js/",
        ],
        alias: {
            // Here we use aliases to map to the minimised versions of the files.
            'app': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'app')),
            'kotlin': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlin')),
            'kotlin-css-js': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlin-css-js')),
            'kotlin-extensions': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlin-extensions')),
            'kotlin-react': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlin-react')),
            'kotlin-react-dom': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlin-react-dom')),
            'kotlin-styled': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlin-styled')),
            'kotlinx-coroutines-core': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlinx-coroutines-core')),
            'kotlinx-html-js': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'kotlinx-html-js')),
            'muirwik-components': path.resolve(path.join(__dirname, 'build/kotlin-js-min/main', 'muirwik-components')),
            '@material-ui': path.resolve(path.join(__dirname, 'node_modules', '@material-ui')),
        }
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            },

            {
                test: /\.js$/,
                include: path.resolve(__dirname, './build/js'),
                loader: require.resolve('source-map-loader'),
                enforce: 'pre',
            },
        ]
    },
    // Might look at splitting later, but for now don't need it, and the minimizer is already on by default in production builds
    // optimization: {
    //     splitChunks: {
    //         cacheGroups: {
    //             commons: {
    //                 test: /[\\/]node_modules[\\/]/,
    //                 name: 'vendor',
    //                 chunks: 'all'
    //             }
    //         }
    //     },
    //     minimizer: [
    //         // we specify a custom UglifyJsPlugin here to get source maps in production
    //         new UglifyJsPlugin({
    //             cache: true,
    //             parallel: true,
    //             uglifyOptions: {
    //                 compress: true,
    //                 ecma: 6,
    //                 mangle: true
    //             },
    //             sourceMap: true
    //         })
    //     ]
    // },

    plugins: [
        // Generate a manifest file which contains a mapping of all asset filenames
        // to their corresponding output file so that tools can pick it up without
        // having to parse `index.html`.
        new ManifestPlugin({
            fileName: 'asset-manifest.json',
        }),
        // ensure that we get a production build of any dependencies
        // this is primarily for React, where this removes 179KB from the bundle
        new webpack.DefinePlugin({
            'process.env.NODE_ENV': '"production"'
        })
    ],
};

module.exports = config;
