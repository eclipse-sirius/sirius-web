/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { gql, useLazyQuery } from '@apollo/client';
import CircularProgress from '@material-ui/core/CircularProgress';
import Tooltip from '@material-ui/core/Tooltip';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { Fragment, useEffect, useState } from 'react';
import {
  GQLHelpTextQueryData,
  GQLHelpTextQueryVariables,
  HelpTooltipProps,
  HelpTooltipState,
} from './HelpTooltip.types';

export const getHelpTextQuery = gql`
  query helpText($editingContextId: ID!, $formId: ID!, $widgetId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $formId) {
          description {
            ... on FormDescription {
              helpText(widgetId: $widgetId)
            }
          }
        }
      }
    }
  }
`;

export function HelpTooltip({ editingContextId, formId, widgetId, children }: HelpTooltipProps) {
  const [state, setState] = useState<HelpTooltipState>({ open: false, content: null });

  const [fetchHelpText, { loading, data, error }] = useLazyQuery<GQLHelpTextQueryData, GQLHelpTextQueryVariables>(
    getHelpTextQuery
  );
  useEffect(() => {
    if (!loading) {
      if (error) {
        setState((prevState) => ({
          ...prevState,
          content: <div>`Error while loading the help text: ${error.message}`</div>,
        }));
      }
      if (data) {
        const text = data.viewer.editingContext.representation.description.helpText;
        if (text === null || text.trim().length === 0) {
          setState((prevState) => ({ ...prevState, content: <i>No help provided</i> }));
        } else {
          const lines = text.split('\n');
          const content = (
            <div>
              {lines.map((line, index) => (
                <Fragment key={index}>
                  {line}
                  <br />
                </Fragment>
              ))}
            </div>
          );
          setState((prevState) => ({ ...prevState, content }));
        }
      }
    }
  }, [loading, data, error]);

  const handleOpen = () => {
    setState((prevState) => ({ ...prevState, open: true, content: <CircularProgress size="1em" /> }));

    const variables: GQLHelpTextQueryVariables = {
      editingContextId,
      formId,
      widgetId,
    };
    fetchHelpText({ variables });
  };

  const handleClose = () => setState((prevState) => ({ ...prevState, open: false, content: null }));

  return (
    <Tooltip
      open={state.open && state.content !== null}
      onClose={handleClose}
      onOpen={handleOpen}
      title={state.content || ''}
      placement={'top'}
      arrow>
      {children || <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />}
    </Tooltip>
  );
}
