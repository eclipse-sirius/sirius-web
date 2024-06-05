/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
const fetch = require("node-fetch");

const accessToken = process.env.GITHUB_TOKEN;
const query = `
  query {
    repository(owner: "eclipse-sirius", name:"sirius-web") {
      issues(states:CLOSED) {
        totalCount
      }
    }
  }`;
const variables = {};

const response = await fetch("https://api.github.com/graphql", {
  method: "POST",
  body: JSON.stringify({ query, variables }),
  headers: {
    Authorization: `Bearer ${accessToken}`,
    "Content-type": "application/json; charset=UTF-8",
  },
});

const body = await response.json();
console.log(body);
