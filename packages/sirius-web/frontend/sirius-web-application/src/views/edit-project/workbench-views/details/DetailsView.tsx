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
import { FormBasedView, FormContext, FormHandle } from '@eclipse-sirius/sirius-components-forms';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, MutableRefObject, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DetailsViewConfiguration, DetailsViewState } from './DetailsView.types';
import { useDetailsViewHandle } from './useDetailsViewHandle';
import { useDetailsViewSubscription } from './useDetailsViewSubscription';
import { GQLDetailsEventPayload, GQLFormRefreshedEventPayload } from './useDetailsViewSubscription.types';

const useDetailsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
  },
}));

const isFormRefreshedEventPayload = (payload: GQLDetailsEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload && payload.__typename === 'FormRefreshedEventPayload';

export const DetailsView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { id, editingContextId, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const [state, setState] = useState<DetailsViewState>({
      form: null,
    });

    const formBasedViewRef: MutableRefObject<FormHandle | null> = useRef<FormHandle | null>(null);

    useDetailsViewHandle(id, formBasedViewRef, ref);

    const { selection } = useSelection();

    const objectIds: string[] = selection.entries.map((entry) => entry.id);
    const skip = objectIds.length === 0;
    const { payload, complete, loading } = useDetailsViewSubscription(editingContextId, objectIds, skip);

    useEffect(() => {
      if (isFormRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, form: payload.form }));
      }
    }, [payload]);

    const { classes } = useDetailsViewStyles();
    const detailsViewConfiguration = initialConfiguration as unknown as DetailsViewConfiguration;
    const initialSelectedPageId = detailsViewConfiguration?.selectedPageId ?? null;

    if (complete || skip) {
      return (
        <div className={classes.idle}>
          <Typography variant="subtitle2">No object selected</Typography>
        </div>
      );
    } else {
      return (
        <Box
          sx={{
            display: 'grid',
            gridTemplateRows: '1fr',
            gridTemplateColumns: '1fr',
          }}
          data-representation-kind="form-details">
          {!state.form || loading ? (
            <Box sx={{ gridRow: '1', gridColumn: '1', zIndex: 1 }}>
              <RepresentationLoadingIndicator />
            </Box>
          ) : null}
          {state.form ? (
            <Box sx={{ gridRow: '1', gridColumn: '1' }}>
              <FormContext.Provider
                value={{
                  payload: payload,
                }}>
                <FormBasedView
                  editingContextId={editingContextId}
                  form={state.form}
                  initialSelectedPageId={initialSelectedPageId}
                  readOnly={readOnly}
                  ref={formBasedViewRef}
                />
              </FormContext.Provider>
            </Box>
          ) : null}
        </Box>
      );
    }
  }
);
