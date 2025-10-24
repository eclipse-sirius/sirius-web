/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import {
  RepresentationComponentProps,
  RepresentationLoadingIndicator,
  WorkbenchMainRepresentationHandle,
} from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { FormContext } from '../contexts/FormContext';
import { Form } from '../form/Form';
import { GQLFormEventPayload, GQLPage } from '../form/FormEventFragments.types';
import { Page } from '../pages/Page';
import { ToolbarAction } from '../toolbaraction/ToolbarAction';
import { FormRepresentationState } from './FormRepresentation.types';
import { useFormSubscription } from './useFormSubscription';
import { GQLFormRefreshedEventPayload } from './useFormSubscription.types';

const useFormRepresentationStyles = makeStyles()((theme) => ({
  page: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(1),
    paddingTop: theme.spacing(1),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    overflowY: 'scroll',
  },
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: theme.spacing(8),
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
    maxHeight: theme.spacing(4),
    textTransform: 'none',
  },
  toolbarAction: {
    paddingRight: theme.spacing(1),
    whiteSpace: 'nowrap',
  },
}));

const isFormRefreshedEventPayload = (payload: GQLFormEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload && payload.__typename === 'FormRefreshedEventPayload';

export const FormRepresentation = forwardRef<WorkbenchMainRepresentationHandle, RepresentationComponentProps>(
  (
    { editingContextId, representationId, readOnly }: RepresentationComponentProps,
    ref: ForwardedRef<WorkbenchMainRepresentationHandle>
  ) => {
    const [state, setState] = useState<FormRepresentationState>({
      form: null,
    });

    useImperativeHandle(
      ref,
      () => {
        return {
          id: representationId,
          applySelection: null,
        };
      },
      []
    );

    const { payload, complete } = useFormSubscription(editingContextId, representationId);
    useEffect(() => {
      if (payload && isFormRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, form: payload.form }));
      }
    }, [payload]);

    const { classes } = useFormRepresentationStyles();

    let content: JSX.Element | null = null;
    if (state.form) {
      const { id } = state.form;
      if (state.form.pages.length > 1) {
        content = (
          <Form
            editingContextId={editingContextId}
            form={state.form}
            initialSelectedPageId={null}
            readOnly={readOnly}
          />
        );
      } else if (state.form.pages.length === 1) {
        const page: GQLPage | null = state.form.pages[0] ?? null;

        if (page) {
          let selectedPageToolbar: JSX.Element | null = null;
          if (page.toolbarActions?.length ?? 0 > 0) {
            selectedPageToolbar = (
              <div className={classes.toolbar}>
                {page.toolbarActions.map((toolbarAction) => (
                  <div className={classes.toolbarAction} key={toolbarAction.id}>
                    <ToolbarAction
                      editingContextId={editingContextId}
                      formId={id}
                      readOnly={readOnly}
                      widget={toolbarAction}
                    />
                  </div>
                ))}
              </div>
            );
          }
          content = (
            <div data-testid="page" className={classes.page}>
              {selectedPageToolbar}
              <Page editingContextId={editingContextId} formId={id} page={page} readOnly={readOnly} />
            </div>
          );
        }
      }
    }

    if (complete) {
      content = (
        <div className={classes.complete}>
          <Typography variant="h6" align="center">
            The form does not exist anymore
          </Typography>
        </div>
      );
    }
    if (!state.form) {
      return <RepresentationLoadingIndicator />;
    }

    return (
      <div data-representation-kind="form">
        <FormContext.Provider
          value={{
            payload: payload,
          }}>
          {content}
        </FormContext.Provider>
      </div>
    );
  }
);
