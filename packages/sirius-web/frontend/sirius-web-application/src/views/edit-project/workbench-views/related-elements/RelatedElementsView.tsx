/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
  RepresentationLoadingIndicator,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import {
  FormBasedView,
  FormContext,
  GQLForm,
  GQLFormRefreshedEventPayload,
  Group,
} from '@eclipse-sirius/sirius-components-forms';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { RelatedElementsViewState } from './RelatedElementsView.types';
import { useRelatedElementsViewSubscription } from './useRelatedElementsViewSubscription';
import { GQLRelatedElementsEventPayload } from './useRelatedElementsViewSubscription.types';

const useRelatedElementsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  content: {
    padding: theme.spacing(1),
  },
}));

const isFormRefreshedEventPayload = (
  payload: GQLRelatedElementsEventPayload
): payload is GQLFormRefreshedEventPayload => payload && payload.__typename === 'FormRefreshedEventPayload';

export const RelatedElementsView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id, editingContextId, readOnly }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const [state, setState] = useState<RelatedElementsViewState>({
      currentSelection: { entries: [] },
      form: null,
    });

    useImperativeHandle(
      ref,
      () => {
        return {
          id,
          getWorkbenchViewConfiguration: () => {
            return {};
          },
        };
      },
      []
    );

    const { selection } = useSelection();

    /**
     * Displays another form if the selection indicates that we should display another properties view.
     */
    const currentSelectionKey: string = state.currentSelection.entries
      .map((entry) => entry.id)
      .sort()
      .join(':');
    const newSelectionKey: string = selection.entries
      .map((entry) => entry.id)
      .sort()
      .join(':');
    useEffect(() => {
      if (selection.entries.length > 0 && currentSelectionKey !== newSelectionKey) {
        setState((prevState) => ({ ...prevState, currentSelection: selection }));
      } else if (selection.entries.length === 0) {
        setState((prevState) => ({ ...prevState, currentSelection: { entries: [] } }));
      }
    }, [currentSelectionKey, newSelectionKey]);

    const objectIds: string[] = state.currentSelection.entries.map((entry) => entry.id);
    const skip = objectIds.length === 0;

    const { payload, complete } = useRelatedElementsViewSubscription(editingContextId, objectIds, skip);
    useEffect(() => {
      if (isFormRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, form: payload.form }));
      }
    }, [payload]);

    const { classes } = useRelatedElementsViewStyles();

    const extractFirstGroup = (editingContextId: string, form: GQLForm, readOnly: boolean): JSX.Element => {
      const group = form.pages[0]?.groups[0];
      if (group) {
        return (
          <div className={classes.content}>
            <Group editingContextId={editingContextId} formId={form.id} readOnly={readOnly} group={group} />
          </div>
        );
      } else {
        return <div className={classes.content} />;
      }
    };

    if (complete || skip) {
      return (
        <div className={classes.idle}>
          <Typography variant="subtitle2">No object selected</Typography>
        </div>
      );
    } else if (!state.form) {
      return (
        <div className={classes.idle}>
          <RepresentationLoadingIndicator />
        </div>
      );
    } else {
      return (
        <div data-representation-kind="form-related-elements">
          <FormContext.Provider
            value={{
              payload: payload,
            }}>
            <FormBasedView
              editingContextId={editingContextId}
              form={state.form}
              readOnly={readOnly}
              postProcessor={extractFirstGroup}
            />
          </FormContext.Provider>
        </div>
      );
    }
  }
);
