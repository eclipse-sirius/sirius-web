/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { useAuth } from 'auth/useAuth';
import { useContext, useEffect, useRef, useState } from 'react';
import { GraphQLClient } from './GraphQLClient';

class ConnectionError extends Error {
  public networkError;
  constructor(message: string) {
    super(message);
  }
}

/**
 * Custom hook to perform a GraphQL query. The API is inspired from, but not identical to, Appolo's
 * own `useQuery` (see https://www.apollographql.com/docs/react/api/react-hooks/#usequery).
 *
 * Usage:
 * ```js
 * const { loading, data, errors } = useQuery(query, vars, operationName);
 * ```
 *
 * If the underlying network operation failed, throws an exception. GraphQL-level errors however are
 * simply reported in the `errors` field of the result. Note that `data` contains the complete result,
 * including `body` and `errors`.
 *
 *
 * @param {*} query the body of the query.
 * @param {*} variables the variables.
 * @param {*} operationName the name of the operation.
 */
export const useQuery = (query, variables, operationName) => {
  const [state, setState] = useState({
    loading: true,
    data: undefined,
    error: undefined,
  });

  const { graphQLHttpClient } = useContext(GraphQLClient);
  const variablesRef = useRef(variables);

  useEffect(() => {
    const fetchData = async () => {
      const response = await graphQLHttpClient.send(query, variablesRef.current, operationName);
      if (!response.ok) {
        const error = new ConnectionError(
          `Network error when executing GraphQL query ${operationName}: ${response.error}`
        );
        error.networkError = response.error;
        setState({ loading: false, data: undefined, error });
      } else {
        let data = undefined;
        let error = undefined;
        if (response.body) {
          if (response.body.data) {
            data = response.body;
          }
          if (response.body.errors) {
            error = Error(`GraphQL error when executing query ${operationName}`);
            error.graphQLErrors = response.body.errors;
          }
        }
        if (!data && !error) {
          error = Error(`An unexpected error has occurred when executing GraphQL query ${operationName}`);
        }
        setState({ loading: false, data, error });
      }
    };
    fetchData();
  }, [graphQLHttpClient, query, operationName, variablesRef]);

  const { error } = state;
  const { clear } = useAuth() as any;
  if (error && error.networkError === 401) {
    clear();
  }
  return state;
};

type LazyQueryTuple = [(variableOverrides: any) => Promise<void>, { loading: boolean; data: any; error: any }];

export const useLazyQuery = (query, variables, operationName): LazyQueryTuple => {
  const [state, setState] = useState({
    loading: true,
    data: undefined,
    error: undefined,
  });

  const { graphQLHttpClient } = useContext(GraphQLClient);

  const request = useRef(async (variableOverrides) => {
    const updatedVariables = { ...variables, ...variableOverrides };
    try {
      const response = await graphQLHttpClient.send(query, updatedVariables, operationName);
      if (!response.ok) {
        const error = new ConnectionError(
          `Network error when executing GraphQL query ${operationName}: ${response.error}`
        );
        error.networkError = response.error;
        setState({ loading: false, data: undefined, error });
      } else {
        let data = undefined;
        let error = undefined;
        if (response.body) {
          if (response.body.data) {
            data = response.body;
          }
          if (response.body.errors) {
            error = Error(`GraphQL error when executing query ${operationName}`);
            error.graphQLErrors = response.body.errors;
          }
        }
        if (!data && !error) {
          error = Error(`An unexpected error has occurred when executing GraphQL query ${operationName}`);
        }
        setState({ loading: false, data, error });
      }
    } catch (error) {
      setState({ loading: false, data: undefined, error: Error('The server cannot be reached.') });
    }
  });

  const { error } = state;
  const { clear } = useAuth() as any;
  if (error && error.networkError === 401) {
    clear();
  }
  return [request.current, state];
};

type MutationTuple = [(variableOverrides: any) => Promise<void>, { loading: boolean; data: any; error: any }];

export const useMutation = (mutation: string, variables, operationName: string): MutationTuple => {
  const [state, setState] = useState({
    loading: true,
    data: undefined,
    error: undefined,
  });

  const { graphQLHttpClient } = useContext(GraphQLClient);

  const mutate = async (variableOverrides) => {
    const updatedVariables = { ...variables, ...variableOverrides };
    try {
      const response = await graphQLHttpClient.send(mutation, updatedVariables, operationName);
      if (!response.ok) {
        const error = new ConnectionError(
          `Network error when executing GraphQL query ${operationName}: ${response.error}`
        );
        error.networkError = response.error;
        setState({ loading: false, data: undefined, error });
      } else {
        let data = undefined;
        let error = undefined;
        if (response.body) {
          if (response.body.data) {
            data = response.body;
          }
          if (response.body.errors) {
            error = Error(`GraphQL error when executing query ${operationName}`);
            error.graphQLErrors = response.body.errors;
          }
        }
        if (!data && !error) {
          error = Error(`An unexpected error has occurred when executing GraphQL query ${operationName}`);
        }
        setState({ loading: false, data, error });
      }
    } catch (error) {
      setState({ loading: false, data: undefined, error: Error('The server cannot be reached.') });
    }
  };

  const { error } = state;
  const { clear } = useAuth() as any;
  if (error && error.networkError === 401) {
    clear();
  }
  return [mutate, state];
};
