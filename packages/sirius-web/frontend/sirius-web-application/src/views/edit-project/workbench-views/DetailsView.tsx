/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { useSelection, WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import { FormBasedView, FormContext } from '@eclipse-sirius/sirius-components-forms';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DetailsViewState } from './DetailsView.types';
import { useDetailsViewSubscription } from './useDetailsViewSubscription';
import { GQLDetailsEventPayload, GQLFormRefreshedEventPayload } from './useDetailsViewSubscription.types';

const useDetailsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
}));

const isFormRefreshedEventPayload = (payload: GQLDetailsEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload && payload.__typename === 'FormRefreshedEventPayload';

export const DetailsView = ({ editingContextId, readOnly }: WorkbenchViewComponentProps) => {
  const [state, setState] = useState<DetailsViewState>({
    currentSelection: { entries: [] },
    form: null,
  });

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
  const { payload, complete } = useDetailsViewSubscription(editingContextId, objectIds, skip);

  useEffect(() => {
    if (isFormRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, form: payload.form }));
    }
  }, [payload]);

  const { classes } = useDetailsViewStyles();

  if (!state.form || complete) {
    return (
      <div className={classes.idle}>
        <Typography variant="subtitle2">No object selected</Typography>
      </div>
    );
  }
  return (
    <div data-representation-kind="form-details">
      <FormContext.Provider
        value={{
          payload: payload,
        }}>
        <FormBasedView editingContextId={editingContextId} form={state.form} readOnly={readOnly} />
      </FormContext.Provider>
    </div>
  );
};
