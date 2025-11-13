/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo and others.
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
import { WorkbenchViewComponentProps, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';

import { ForwardedRef, forwardRef, ReactNode, useEffect, useImperativeHandle, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { useValidationViewSubscription } from './useValidationViewSubscription';
import {
  Category,
  GQLValidationEventPayload,
  GQLValidationRefreshedEventPayload,
  Validation,
} from './useValidationViewSubscription.types';
import { ValidationRepresentationState } from './Validation.types';

const useValidationViewStyle = makeStyles()((theme) => ({
  root: {
    padding: '8px',
  },
  heading: {
    flexBasis: '33.33%',
    flexShrink: 0,
  },
  secondaryHeading: {
    color: theme.palette.text.secondary,
  },
  accordionDetailsRoot: {
    flexDirection: 'column',
  },
  divider: {
    margin: '8px 0',
  },
  idle: {
    padding: theme.spacing(1),
  },
}));

const isValidationRefreshedEventPayload = (
  payload: GQLValidationEventPayload | null
): payload is GQLValidationRefreshedEventPayload =>
  !!payload && payload.__typename === 'ValidationRefreshedEventPayload';

export const ValidationView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id, editingContextId }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const { classes } = useValidationViewStyle();
    const { payload, complete } = useValidationViewSubscription(editingContextId);
    const { t } = useTranslation('sirius-components-validation', { keyPrefix: 'validationView' });

    const [state, setState] = useState<ValidationRepresentationState>({
      validationPayload: null,
    });

    useImperativeHandle(
      ref,
      () => {
        return {
          id,
          getWorkbenchViewConfiguration: () => {
            return {};
          },
          applySelection: null,
        };
      },
      []
    );

    useEffect(() => {
      if (isValidationRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, validationPayload: payload.validation }));
      }
    }, [payload]);

    const categories: Category[] = [];
    const processedValidation: Validation = { categories };

    let noDiagnostic: ReactNode = (
      <div className={classes.idle}>
        <Typography variant="subtitle2">{t('noDiagnostic')}</Typography>
      </div>
    );

    if (state.validationPayload && !complete) {
      state.validationPayload.diagnostics.forEach((diagnostic) => {
        let category: Category | undefined = categories.find((category) => category.kind === diagnostic.kind);
        if (!category) {
          category = {
            kind: diagnostic.kind,
            diagnostics: [],
          };
          categories.push(category);
        }

        category.diagnostics.push({ id: diagnostic.id, message: diagnostic.message });
      });

      const accordions = processedValidation.categories.map((category) => {
        const details = category.diagnostics
          .map<React.ReactNode>((diagnostic) => {
            return <Typography key={diagnostic.id}>{diagnostic.message}</Typography>;
          })
          .reduce((prev, current, index) => [
            prev,
            <Divider key={`Divider-${index}`} className={classes.divider} />,
            current,
          ]);

        return (
          <Accordion key={category.kind}>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography className={classes.heading}>{category.kind}</Typography>
              <Typography className={classes.secondaryHeading}>
                {t('diagnosticCount', { count: category.diagnostics.length })}
              </Typography>
            </AccordionSummary>
            <AccordionDetails className={classes.accordionDetailsRoot}>{details}</AccordionDetails>
          </Accordion>
        );
      });

      if (accordions.length > 0) {
        return <div className={classes.root}>{accordions}</div>;
      } else {
        return noDiagnostic;
      }
    } else {
      return noDiagnostic;
    }
  }
);
