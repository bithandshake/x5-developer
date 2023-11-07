
### x.developer.api

Functional documentation of the x.developer.api ClojureScript namespace

---

##### [README](../../../../README.md) > [DOCUMENTATION](../../../COVER.md) > x.developer.api

### Index

- [request-inspector](#request-inspector)

- [route-browser](#route-browser)

---

### request-inspector

```
@description
Request history inspector for requests sent by the 'x.sync' module.
```

<details>
<summary>Source code</summary>

```
(defn view
  []
  [:<> [ugly-elements/import-styles]
       (if-let [request-id @request-inspector.state/INSPECTED-REQUEST]
               [request-view]
               [request-list])])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [x.developer.api :refer [request-inspector]]))

(x.developer.api/request-inspector)
(request-inspector)
```

</details>

---

### route-browser

```
@description
Route browser for client-side routes handled by the 'x.router' module.
```

<details>
<summary>Source code</summary>

```
(defn view
  []
  [:<> [ugly-elements/import-styles]
       [route-filter-field]
       [route-list]])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [x.developer.api :refer [route-browser]]))

(x.developer.api/route-browser)
(route-browser)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

